import Link from 'next/link'
import Head from 'next/head';
import Page from '../layouts/main'

const Index = () => (
    <Page>
        <h1>
            HOME
        </h1>
        <h2>
            <Link href="/about">
                <a style={{background: 'black', color: 'white'}}>소개</a>
            </Link>
        </h2>
    </Page>
);

export default Index;