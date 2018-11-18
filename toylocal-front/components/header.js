import Link from 'next/link';

const linkStyle = {
    marginRight: '1rem'
}
const Header = () => {
    return (
        <div>
            <Link href="/"><a style={linkStyle}>홈</a></Link>
            <Link prefetch href="/search"><a style={linkStyle}>검색</a></Link>
            <Link prefetch href="/TableExampleSortable"><a style={linkStyle}>테이블</a></Link>
        </div>
    );
};

export default Header;