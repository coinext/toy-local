import Router from 'next/router'
import Axios from 'axios';

export default class extends React.Component {
    static async getInitialProps({ req }) {
        delete Axios.defaults.headers.common['Authorization'];
        Router.push('/');
    }

    render() {
        return (
            <div />
        )
    }
}