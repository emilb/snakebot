import React from 'react'

class Tile extends React.Component {
    render() {
        var styles = {
            empty: {
                border: "1px solid black",
                padding: 0,  // Becomes "10px" when rendered.
                background: "white",
                width: 66,
                height: 66
            },

            food: {
                border: "1px solid black",
                padding: 0,  // Becomes "10px" when rendered.
                background: "red",
                width: 66,
                height: 66
            },
            obstacle: {
                border: "1px solid black",
                background: "black",
                padding: 0,
                width: 66,
                height: 66
            },
            snakebody: {
                border: "1px solid black",
                background: "blue",
                padding: 0,
                width: 66,
                height: 66
            },
            snakehead: {
                border: "1px solid black",
                padding: 0,  // Becomes "10px" when rendered.
                background: "#9933ff",
                width: 66,
                height: 66

            }
        };
        return (
            <div style={{width: 66, height: 66, display: "inline-block"}}>
                {(() => {
                    console.log(this.props.tile);
                    switch (this.props.tile[this.props.index].content) {
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
    index: React.PropTypes.number.isRequired
};

export default Tile