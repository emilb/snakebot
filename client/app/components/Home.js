//import React from 'react';
//import SockJS from 'sockjs-client'
//import GameBoard from './GameBoard'
//
//class Home extends React.Component {
//    constructor(props) {
//        super(props);
//        this.state = {
//            data: {},
//            socket: new SockJS('http://localhost:8080/events')
//        }
//    }
//
//    componentDidMount() {
//        var sock = this.state.socket;
//        sock.onopen = function () {
//            console.log('open');
//        };
//        sock.onmessage = function (e) {
//            console.log("hello");
//            var jsonData = JSON.parse(e.data);
//            console.log(jsonData);
//            this.setState({
//                data: jsonData
//            });
//        }.bind(this);
//        sock.onclose = function () {
//            console.log('close');
//        };
//    }
//
//    render() {
//        return (
//            <div>
//                <GameBoard world={this.state.data.map.tiles} width={this.state.data.map.width}
//                           height={this.state.data.map.height}/>
//            </div>
//        )
//    }
//}
//
//export default Home;