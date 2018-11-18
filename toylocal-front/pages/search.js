import Router from 'next/router'
import axios from 'axios';
import Page from '../layouts/main'

class Search extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            books: '',
            meta: '',
            keyword: ''
        }
    }

    static async getInitialProps ({req, res}) {
    }

    handleChange (key, event) {
        this.setState({[key]: event.target.value})
    }

    handleRefresh = async(e) => {
        console.log(this.state.keyword);
        const response = await axios.get(`http://52.79.128.170:8080/v1/book/search?query=${this.state.keyword}&page=1&size=10&vendor=kakao`);
        
        var bookList;
        if(response.data.data != null) {
            bookList = response.data.data.map(
                book => <li key={book.isbn}>{book.title}</li>
            )
        }
        this.setState({
            books: bookList,
            meta: response.data.meta
        });
    }

    render() {
        return (
            <Page>
                <input type='text' value={this.state.keyword} onChange={this.handleChange.bind(this, 'keyword')}/>
                <div>
                    <button onClick={this.handleRefresh}>검색</button>
                </div>
                <ul>
                    {this.state.books}
                </ul>

                <div>{this.state.meta.totalCount}</div>
            </Page>
        );
    }
}

export default Search;