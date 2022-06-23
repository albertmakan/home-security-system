import React, { useState, useEffect } from 'react';
import LogsService from '../../services/LogsService';
import Table from 'react-bootstrap/Table';
import SearchFilterBar from '../search-filter-bar/SearchFilterBar'
const LogsPage = () => {
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        LogsService.getAll().then((response) => {
            setLogs(response);
        });
    }, []);

    const onSearch = (searchInput, filterValue, regexSelected, chosenDate) => {
        console.log(searchInput, filterValue, regexSelected, chosenDate)
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
                            <td>{new Date(l.id.date).toISOString()}</td>
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
