import React from 'react'

class Tile extends React.Component {
    render() {
        var styles = {
            empty: {
                border: "1px solid black",
                padding: 0,  // Becomes "10px" when rendered.
                background: "white",
                width: this.props.width,
                height:  this.props.height
            },

            food: {

                padding: 0,  // Becomes "10px" when rendered.
                background: "red",
                width: this.props.width,
                height:  this.props.height
            },
            obstacle: {

                background: "black",
                padding: 0,
                width: this.props.width,
                height:  this.props.height
            },
            snakebody: {

                background: "blue",
                padding: 0,
                width: this.props.width,
                height: this.props.height
            },
            snakehead: {

                padding: 0,  // Becomes "10px" when rendered.
                background: "#9933ff",
                width: this.props.width,
                height: this.props.height

            }
        };

        return (
            <div style={{width: this.props.width, height: this.props.height, display: "inline-block"}}>
                {(() => {

                    switch (this.props.tile.content) {
                        case "empty":
                            return <div style={styles.empty}> </div>;
                        case "food":
                            return <div style={styles.food}>  </div>;
                        case "obstacle":
                            return <div style={styles.obstacle}>  </div>;
                        case "snakebody":
                            return <div style={styles.snakebody}>  </div>;
                        case "snakehead":
                            return <div style={styles.snakehead}>  </div>;
                    }
                })()}
            </div>
        )
    }
}



Tile.PropTypes = {
    tile: React.PropTypes.object.isRequired,
    width: React.PropTypes.number.isRequired,
    height: React.PropTypes.number.isRequired
};

export default Tile