//import React from 'react';
//import GameBoard from './GameBoard'
////import React from 'react';
//import GameBoard from './GameBoard'
//import getGame from '../utils/helpers';
//
//
//class GameControls extends React.Component {
//    constructor(props) {
//        super(props);
//        this.state = {
//            world: [],
//            id: {}
//        }
//    }
//
//    componentDidMount() {
//        this.init();
//    };
//
//
//    init() {
//        getGame().then(function (data) {
//            console.log(JSON.stringify(data.world.tiles));
//            this.setState({
//                world: data.world.tiles
//            })
//        }.bind(this));
//    };
//
//    componentWillReceiveProps(nextProps) {
//        this.init(nextProps.params.world);
//    };
//
//
//    render() {
//        return (
//            <div className="col-sm-12 col-md-12">
//                <GameBoard world={this.state.world}/>
//            </div>
//        )
//    }
//}
//
//export default GameControls;
//import getGame from '../utils/helpers';
//
//
//class GameControls extends React.Component {
//    constructor(props) {
//        super(props);
//        this.state = {
//            world: [],
//            id: {}
//        }
//    }
//
//    componentDidMount() {
//        this.init();
//    };
//
//
//    init() {
//        getGame().then(function (data) {
//            console.log(JSON.stringify(data.world.tiles));
//            this.setState({
//                world: data.world.tiles
//            })
//        }.bind(this));
//    };
//
//    componentWillReceiveProps(nextProps) {
//        this.init(nextProps.params.world);
//    };
//
//
//    render() {
//        return (
//            <div className="col-sm-12 col-md-12">
//                <GameBoard world={this.state.world}/>
//            </div>
//        )
//    }
//}
//
//export default GameControls;
