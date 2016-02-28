import React from 'react'
import _ from 'lodash'
import Tile from './Tile'
import GameDropDown from './GameDropDown'
import SockJS from 'sockjs-client'
import { Button, Glyphicon,DropdownButton, MenuItem, Grid, Row, Col, ListGroup, ListGroupItem, PageHeader } from 'react-bootstrap';
import GetGames from '../utils/helpers'

class GameBoard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            world: [],
            width: {},
            height: {},
            tileHeight: {},
            tileWidth: {},
            snakes: [],
            gameList: [],
            socket: new SockJS('http://localhost:8080/events')
        }
    }

    componentDidMount() {
        var sock = this.state.socket;
        //sock.onopen = function () {
        //    console.log('open');
        //};
        //sock.onmessage = function (e) {
        //    var jsonData = JSON.parse(e.data);
        //
        //    this.setState({
        //        world: jsonData.map.tiles,
        //        width: jsonData.map.width,
        //        height: jsonData.map.height,
        //        tileHeight: this.getHeightPerRow(),
        //        tileWidth: this.getWidthPerRow(),
        //        snakes: jsonData.map.snakeInfos
        //    });
        //}.bind(this);
        //sock.onclose = function () {
        //    console.log('close');
        //};

        GetGames().then(function (data) {
            this.setState({
                gameList: data
            })
        }.bind(this));
    }

    updateList() {
        GetGames().then(function (data) {
            this.setState({
                gameList: data
            })
        }.bind(this));
    }

    componentWillReceiveProps(nextProps) {
        this.init(nextProps.params.world);
    };

    getHeightPerRow() {
        return 1000 / this.state.height
    }

    getWidthPerRow() {
        return 1500 / this.state.height

    }

    render() {
        var _this = this;
        var tiles = [];
        if (this.state.world.length > 0) {
            for (var i = 0; i < _this.state.width; i++) {
                var tileRow = [];
                for (var j = 0; j < _this.state.width; j++) {
                    tileRow.push(<Tile tile={_this.state.world[j][i]}
                                       height={_this.state.tileHeight}
                                       width={_this.state.tileWidth}/>
                    )
                }
                tiles.push(tileRow)
            }
        }


        return (
            <Grid fluid>
                <Row className="show-grid">
                    <Col xs={3} md={2} lg={2} xsOffset={8} mdOffset={5} lgOffset={5}>
                        <PageHeader style={{textAlign: "center"}}>WELCOME TO SNAKE
                        </PageHeader>
                    </Col>
                </Row>


                <Row className="show-grid">

                        <Col xs={6} md={4} lg={4} xsOffset={6} mdOffset={3} lgOffset={3}>
                            <GameDropDown gameList={this.state.gameList}/>
                        </Col>
                        <Col xs={4} md={3} lg={3} xsOffset={1} mdOffset={1} lgOffset={1}>
                            <Button bsSize="large" onclick={() => this.updateList()}><Glyphicon glyph="align-left"/>Update List</Button>
                        </Col>
                </Row>

                <Row className="show-grid">
                    <Col xs={18} md={12} lg={12}>
                        <Col xs={2} md={2} lg={2}>
                        </Col>

                        <Col xs={12} md={8} lg={8}>
                            < div style={{border: "25px solid black", width: 1548, height: 1040}}>
                                {tiles.map(tile => {
                                    return (
                                        <div style={{width: 1500, height: _this.state.tileHeight}}>
                                            {tile}
                                        </div>
                                    )
                                })}
                            </div>
                        </Col>
                        <Col xs={2} md={2} lg={2}>
                            <ListGroup> {this.state.snakes.map(function (snake) {
                                console.log(snake);
                                return (
                                    <ListGroupItem> {snake.name} {snake.length} </ListGroupItem>
                                )
                            })}
                            </ListGroup>
                        </Col>
                    </Col>
                </Row>
            </Grid>
        )
    }
}


export default GameBoard;