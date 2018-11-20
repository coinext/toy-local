import _ from 'lodash'
import React, { Component } from 'react'
import Link from 'next/link'
import Axios from 'axios'
import { Header, Image, Table, Icon, Pagination, Input, Popup, Button } from 'semantic-ui-react'
import Page from '../layouts/main'
import getConfig from 'next/config'
import Router from 'next/router'

export default class Search extends Component {
  state = {
    keyword: '',
    column: 'created',
    data: null,
    direction: null,
    activePage: 1,
    totalPages: 0,
    size: 10,
  }

  componentDidMount() {
    Axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
  }

  handleRefresh = () => {
    const { keyword, column, activePage, size } = this.state
    const { publicRuntimeConfig } = getConfig()

    Axios.get(`${publicRuntimeConfig.serverEndpoint}/v1/book/search?query=${keyword}&page=${activePage}&size=${size}&vendor=kakao`)
      .then(response => {
        this.setState({
          data: response.data.data,
          column: column,
          totalPages: Math.ceil(response.data.meta.totalCount / size)
        })
      })
      .catch(error => {
        Router.push('/login')
      })
  }

  handleBookmark = (title, url, isbn) => {
    const { publicRuntimeConfig } = getConfig()

    Axios.post(`${publicRuntimeConfig.serverEndpoint}/v1/bookmark`, {
      title: title,
      url: url,
      isbn: isbn,
      vendor: 'kakao'
    })
    .then(response => {
    })
    .catch(error => {
    });
  }

  handleChange = (key, event) => {
    this.setState({[key]: event.target.value})
  }

  handleSort = clickedColumn => () => {
    const { column, data, direction } = this.state

    if (column !== clickedColumn) {
      this.setState({
        column: clickedColumn,
        data: _.sortBy(data, [clickedColumn]),
        direction: 'ascending',
      })

      return
    }

    this.setState({
      data: data.reverse(),
      direction: direction === 'ascending' ? 'descending' : 'ascending',
    })
  }

  handleKeyDown = (e, cb) => {
    if (e.key === 'Enter' && e.shiftKey === false) {
      e.preventDefault();
      cb();
    }
  };

  handlePaginationChange = (e, { activePage }) =>
    this.setState({ activePage }, () => { this.handleRefresh() })

  render() {
    const { keyword, column, data, direction, activePage, totalPages } = this.state

    return (
        <Page>
            <Input
                icon={<Icon name='search' inverted circular link onClick={this.handleRefresh} />}
                placeholder='Search...'
                onChange={this.handleChange.bind(this, 'keyword')}
                onKeyDown={(e) => { this.handleKeyDown(e, this.handleRefresh); }} />
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
                        sorted={column === 'price' ? direction : null}
                        onClick={this.handleSort('price')}
                        >
                        가격
                        </Table.HeaderCell>
                        <Table.HeaderCell collapsing>
                        북마크
                        </Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                {_.map(data, ({ title, authors, publisher, thumbnail, price, url, isbn}, index) => (
                    <Table.Row key={index}>
                        <Table.Cell>
                        <Header as='h4' image>
                            <Image src={thumbnail} rounded size='mini' />
                            <Header.Content>
                                <Link href={url}><a target="_blank">{title}</a></Link>
                                <Header.Subheader>{authors.join(', ')} | {publisher}</Header.Subheader>
                            </Header.Content>
                        </Header>
                        </Table.Cell>
                        <Table.Cell collapsing>{price}</Table.Cell>
                        <Table.Cell collapsing textAlign='center'>
                          <Popup
                            trigger={<Icon name='bookmark' onClick={() => this.handleBookmark(title, url, isbn)} />}
                            content={'BookMark!!'}
                            on='click'
                          />
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