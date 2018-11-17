import Header from '../components/header'
import Footer from '../components/footer'

const Main = ({children}) => (
    <div>
        <Header/>
        { children }
        <Footer />
  </div>
);

export default Main;