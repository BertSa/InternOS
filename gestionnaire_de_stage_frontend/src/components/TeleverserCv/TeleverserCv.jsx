import './TeleverserCv.css'
import React, {Component} from "react";
import Dropzone from "react-dropzone";


class TeleverserCv extends Component {

    onDrop = acceptedFiles => {
        console.log(acceptedFiles)
    }

    render() {

        return (
            <Dropzone onDrop={this.onDrop} accept="application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                {({getRootProps, getInputProps, isDragActive, isDragReject}) => (
                    <div {...getRootProps()}>
                        <input {...getInputProps()}/>
                        {!isDragActive && "Cliquer moi ou glisser votre C.V. ici"}
                        {isDragActive && !isDragReject && "Déposez votre C.V ici"}
                        {isDragActive && isDragReject && "Ce format de fichier n'est pas accepté"}
                    </div>
                )}
            </Dropzone>
        );
    }
}
export default TeleverserCv