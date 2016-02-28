import React from 'react'
import { DropdownButton, MenuItem, Row, Col} from 'react-bootstrap';

class GameDropDown extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            gameList: []
        }
    }

    componentWillReceiveProps(newProps) {
            this.setState({
                gameList: newProps.gameList.games
            })
    }

    render() {
        var games = [];
        console.log(this.state.gameList.games);
        for(var i = 0; i < this.state.gameList.length; i++) {
            console.log(this.state.gameList[i]);
            games.push(<MenuItem style={{width: 1000, height: 50, fontSize: 40}}
                      eventKey="1">{this.state.gameList[i]}</MenuItem>)
        }

        return (

                    <DropdownButton style={{width: 1000, height: 200, fontSize: 40}} bsSize="large"
                                    title="AVAILABLE GAMES" pullRight id="split-button-pull-right">
                        {games}
                    </DropdownButton>

        )
    }
}

GameDropDown.PropTypes = {
    gameList: React.PropTypes.array.isRequired
};


export default GameDropDown;