import Link from 'next/link'
import Axios from 'axios';
import Router from 'next/router'
import Page from '../layouts/main'

class Index extends React.Component {
    static async getInitialProps({ req, res }) {
        if (!Axios.defaults.headers.common['Authorization']) {
            if (res) {
                res.writeHead(302, {
                    Location: '/login'
                })
                res.end()
            } else {
                Router.push('/login')
            }
        }

        return {}
    }

    render() {
        return (
            <Page>
                <h1>
                    HOME
                </h1>
            </Page>
        )
    }
}

export default Index;