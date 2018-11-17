import axios from 'axios';
import Page from '../layouts/main'

class Search extends React.Component {
    static async getInitialProps ({req}) {
        const response = await axios.get('http://localhost:8080/v1/book/search?query=%ED%95%98%EB%8A%98&page=1&size=10&vendor=kakao');
        return {
            books: response.data.data,
            meta: response.data.meta
        }
    }

    render() {
        const { books } = this.props;
        const { meta } = this.props;


        console.log(meta);
        const bookList = books.map(
            book => <li key={book.isbn}>{book.title}</li>
        )
        
        return (
            <Page>
                <ul>
                    {meta.totalCount}
                </ul>
            </Page>
        );
    }
}

export default Search;