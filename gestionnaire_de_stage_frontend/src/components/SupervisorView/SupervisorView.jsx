import React from "react";
import {Route, useRouteMatch} from "react-router-dom";
import {useAuth} from "../../services/use-auth";
import SupervisorVisitForm from "./VisitForm/SupervisorVisitForm";
import StudentStatusView from "./StudentStatusView/StudentStatusView";
import {Title} from "../SharedComponents/Title/Title";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";

export default function SupervisorView() {
    const {path} = useRouteMatch();
    let auth = useAuth();
    return (<>
        <Route exact path={`${path}/supervisor_form_visit_company`}>
            <ContainerBox>
                <SupervisorVisitForm/>
            </ContainerBox>
        </Route>
        <Route exact path={`${path}/students/status`}>
            <Title>Status des étudiants qui vous sont attribués</Title>
            <StudentStatusView/>
        </Route>
        <Route exact path={`${path}`}>
            <h1 className="text-center">Bonjour {auth.user.firstName}!</h1>
        </Route>
    </>);
}
