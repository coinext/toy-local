import React from 'react';
import Page from '../layouts/main'

const Keyword = ({url}) => {
    return (
        <Page>
            -- {url.query.keyword}
        </Page>
    );
};

export default Keyword;