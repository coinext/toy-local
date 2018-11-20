import _ from 'lodash'
import React, { Component } from 'react'
import Axios from 'axios'
import { Table, Icon, Pagination } from 'semantic-ui-react'
import Page from '../layouts/main'
import getConfig from 'next/config'
import Router from 'next/router'

export default class History extends Component {
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

    Axios.get(`${publicRuntimeConfig.serverEndpoint}/v1/history?page=${activePage}&size=${size}&direction=${this.sortMapping[direction]}&sort=${column}`)
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
        <Table sortable celled fixed>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell
                sorted={column === 'keyword' ? direction : null}
                onClick={this.handleSort('keyword')}
              >
                검색어
              </Table.HeaderCell>
              <Table.HeaderCell
                sorted={column === 'created' ? direction : null}
                onClick={this.handleSort('created')}
              >
                검색 일시
              </Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {_.map(data, ({ keyword, created}, index) => (
              <Table.Row key={index}>
                <Table.Cell>{keyword}</Table.Cell>
                <Table.Cell>{created}</Table.Cell>
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