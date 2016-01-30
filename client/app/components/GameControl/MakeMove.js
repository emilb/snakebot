import React from 'react';
import makeMove from '../../utils/helpers';

class MakeMove extends React.Component {

    getRef(direction) {
        this.direction = direction;
    };

    handleNewGame() {
        //const size = this.size.value;
        //this.size.value = '';
        this.props.world = startNewGame();
    };

    handleMakeMove() {
        const direction = this.direction.value;
        this.direction.value = '';
        this.props.world = makeMove(direction);
    }

    render() {
        return (
            <form onSubmit={() => this.handleMakeMove()}>
                <div className="form-group col-sm-2">
                    <input type="text" className="form-control" ref={(ref) => this.getRef(ref)}/>
                </div>
                <div className="form-group col-sm-1">
                    <button type="submit" className="btn btn-block btn-primary"> Make Move</button>
                </div>
            </form>
        )
    }
}

export default MakeMove;
