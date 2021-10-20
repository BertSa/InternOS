import React, {Component} from 'react';
import './ListStudentView.css'


export default class ListStudentView extends Component {

    render() {
        const {student} = this.props.cv;
        return <div className="shadow-lg rounded-top p-3 mt-3 border-left border-right">
            <div className={'d-flex align-items-center flex-column'}>
                <h3 className={'d-inline-block text-dark'}>
                    <span className={"badge rounded-pill"} >
                         {student.firstName} {student.lastName}
                    </span>
                </h3>
            </div>
        </div>
    }
}
