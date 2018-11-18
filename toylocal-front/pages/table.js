import React from 'react'
import axios from 'axios'
import Link from 'next/link'
// import { style } from 'next/css'
import Form from '../components/form'

export default class Table extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
        books: props.books
    }
  }

  handleFormSubmit = async(e) => {
    const response = await axios.get('http://52.79.128.170:8080/v1/book/search?query=%ED%95%98%EB%8A%98&page=1&size=10&vendor=kakao');
    const bookList = response.data.data.map(
        book => {
            <tr>
                <td>{ book.title }</td>
                <td><a href={'${book.url}'}/>{ book.isbn }</td>
            </tr>
        }
    )
    this.setState({
        books: bookList
    });
  }

  render () {
    return (
      <div>
        <h1>Rotten <strike>tomatoes</strike> pepper</h1>
        <strong>Movie: Infinity wars </strong>
        <Form handleFormSubmit={this.handleFormSubmit.bind(this)} />
        <div>
            <div>
                <h3> NEXTHRONE - THE REVELATION OF GAME OF THRONES' CHARACTERS </h3>
            </div>
            <table>
                <thead>
                <tr>
                    <th>Character</th>
                    <th>Real Name</th>
                </tr>
                </thead>
                <tbody>
                    {this.state.books}
                </tbody>
            </table>
        </div>

      </div>
    )
  }
}

const styles = {
    th: {
      background: '#00cccc',
      color: '#fff',
      textTransform: 'uppercase',
      fontSize: '12px',
      padding: '12px 35px',
    },
  
    header: {
      font: '15px Monaco',
      textAlign: 'center'
    },
  
    table: {
      fontFamily: 'Arial',
      margin: '25px auto',
      borderCollapse: 'collapse',
      border: '1px solid #eee',
      borderBottom: '2px solid #00cccc'
    },
  
    td: {
      color: '#999',
      border: '1px solid #eee',
      padding: '12px 35px',
      borderCollapse: 'collapse'
    },
  
    list: {
      padding: '50px',
      textAlign: 'center'
    },
  
    photo: {
      display: 'inline-block'
    },
  
    photoLink: {
      color: '#333',
      verticalAlign: 'middle',
      cursor: 'pointer',
      background: '#eee',
      display: 'inline-block',
      width: '250px',
      height: '250px',
      lineHeight: '250px',
      margin: '10px',
      border: '2px solid transparent',
      ':hover': {
        borderColor: 'blue'
      }
    }
  }