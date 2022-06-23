import React, { useState, useEffect } from 'react';
import LogsService from '../../services/LogsService';
import Table from 'react-bootstrap/Table';
import SearchFilterBar from '../search-filter-bar/SearchFilterBar'
import moment from "moment";


const LogsPage = () => {
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        LogsService.getAll().then((response) => {
            setLogs(response);
        });
    }, []);

    const onSearch = (searchInput, level, regexSelected, chosenDate) => {
        console.log(searchInput, level, regexSelected, chosenDate);
        regexSelected = regexSelected === "on" ? true : false;
    
        LogsService.searchFilter(searchInput, regexSelected, level, chosenDate).then((response) => {
            setLogs(response);
        });

    }

    return (
        <div>
            <SearchFilterBar searchText="Search logs..." filterText="Filter by level" filterValues={["INFO", "WARN", "ERROR"]} onSearch={onSearch} regexSearch={true} dateSearch={true}></SearchFilterBar>
            <Table>
                <thead>
                    <tr>
                        <th>Timestamp</th>
                        <th>Level</th>
                        <th>Message</th>
                    </tr>
                </thead>
                <tbody>
                    {logs.map((l, i) => (
                        <tr key={i}>
                            <td>{moment(l.id.date).format("DD-MM-YYYY HH:mm")}</td>
                            <td>{l.level}</td>
                            <td>{l.message}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default LogsPage;
