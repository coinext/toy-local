import _ from 'lodash'
import React, { Component } from 'react'
import Axios from 'axios'
import { Table, Icon, Pagination } from 'semantic-ui-react'
import Page from '../layouts/main'
import getConfig from 'next/config'
import Router from 'next/router'
import Link from 'next/link'

export default class Bookmark extends Component {
  state = {
    column: 'created',
    data: null,
    direction: 'descending',
    activePage: 1,
    totalPages: 0,
    size: 10,
  }

  sortMapping = {
    'ascending': 'ASC',
    'descending': 'DESC',
  }

  componentDidMount() {
    Axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
    this.handleRefresh()
  }

  handleRefresh = () => {
    const { column, direction, activePage, size } = this.state
    const { publicRuntimeConfig } = getConfig()

    Axios.get(`${publicRuntimeConfig.serverEndpoint}/v1/bookmark?page=${activePage}&size=${size}&direction=${this.sortMapping[direction]}&sort=${column}`)
      .then(response => {
        this.setState({
          data: response.data.data,
          column: column,
          direction: direction,
          totalPages: Math.ceil(response.data.meta.totalCount / size)
        })
      })
      .catch(error => {
        Router.push('/login')
      })
  }

  handleBookmarkDelete = (id) => {
    const { publicRuntimeConfig } = getConfig()

    Axios.delete(`${publicRuntimeConfig.serverEndpoint}/v1/bookmark/${id}`)
      .then(response => {
        this.handleRefresh()
      })
      .catch(error => {
        Router.push('/login')
      });
  }

  handleSort = clickedColumn => () => {
    const { direction } = this.state

    this.setState({
      column: clickedColumn,
      direction: direction === 'ascending' ? 'descending' : 'ascending'
    },() => { this.handleRefresh() })
  }

  handlePaginationChange = (e, { activePage }) =>
    this.setState({ activePage }, () => { this.handleRefresh() })

  render() {
    const { column, data, direction, activePage, totalPages } = this.state

    return (
      <Page>
        <Table sortable celled>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell
                sorted={column === 'title' ? direction : null}
                onClick={this.handleSort('title')}
              >
                제목
              </Table.HeaderCell>
              <Table.HeaderCell collapsing
                sorted={column === 'created' ? direction : null}
                onClick={this.handleSort('created')}
              >
                북마크 일시
              </Table.HeaderCell>
              <Table.HeaderCell collapsing>
                삭제
              </Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {_.map(data, ({ id, title, url, isbn, created}, index) => (
              <Table.Row key={index}>
                <Table.Cell>
                  <Link href={url}><a target="_blank">{title}</a></Link>
                </Table.Cell>
                <Table.Cell collapsing>{created}</Table.Cell>
                <Table.Cell collapsing textAlign='center'>
                  <Icon name='delete' onClick={() => this.handleBookmarkDelete(id)} />
                </Table.Cell>
              </Table.Row>
            ))}
          </Table.Body>
        </Table>
        <Pagination
          onPageChange={this.handlePaginationChange}
          defaultActivePage={activePage}
          ellipsisItem={{ content: <Icon name='ellipsis horizontal' />, icon: true }}
          firstItem={{ content: <Icon name='angle double left' />, icon: true }}
          lastItem={{ content: <Icon name='angle double right' />, icon: true }}
          prevItem={{ content: <Icon name='angle left' />, icon: true }}
          nextItem={{ content: <Icon name='angle right' />, icon: true }}
          totalPages={totalPages}
        />
      </Page>
    )
  }
}