import React from 'react';
import NewGame from './GameControl/NewGame'
import MakeMove from './GameControl/MakeMove'
import AddPlayer from './GameControl/AddPlayer'
import StartGame from './GameControl/StartGame'
import startNewGame from '../utils/helpers';


class GameControls extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            world: {},
            id: {}
        }
    }

    newGame() {
        startNewGame().then(function (data) {
            this.setState({
                world: data.worldmatrix
            })
        }.bind(this));
    }

    render() {
        return (
            <div className="col-sm-12 col-md-12">
                <NewGame newGame={() => this.newGame()}/>
                <AddPlayer />
                <StartGame />
                <MakeMove />
            </div>
        )
    }
}

export default GameControls;
