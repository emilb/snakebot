import React from 'react'
import _ from 'lodash'
import getGame from '../utils/helpers';
import Tile from './Tile'

class GameBoard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            world: [],
            id: {},
            width: {},
            height: {}
        }
    }

    componentDidMount() {
        this.init();
    };


    init() {
        getGame().then(function (data) {

            this.setState({
                world: data.world.tiles,
                width: data.world.width,
                height: data.world.height
            })
        }.bind(this));
    };

    componentWillReceiveProps(nextProps) {
        this.init(nextProps.params.world);
    };

    getHeightPerRow() {
        return 1000 / this.state.height
    }

    getWidthPerRow() {
        return 1000 / this.state.width
    }


    render() {
        var _this = this;

        return (
            <div className="col-lg-8 col-lg-offset-2">
                <div style={{border: "5px solid black", width: 1000, height: 1000}}>
                    { _.times(2, (function (value) {
                        _this.state.world.map(function (tiles, index) {
                            console.log(tiles[value])
                            return (
                                <Tile tile={tiles[value]} index={index}/>
                            )
                        })
                    }))}
                </div>
            </div>


        )
    }
}


export default GameBoard;