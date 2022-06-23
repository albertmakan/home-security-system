import React from "react";
import { useRef } from "react";

const SearchFilterBar = ({ searchText, filterText, filterValues, onSearch, regexSearch = false, dateSearch = false }) => {

    const searchInputEl = useRef("");
    const filterSelectEl = useRef("NO_VALUE");
    const regexCheckEl = useRef(false);
    const dateEl = useRef(new Date());


    return (
        <div className="d-flex">
            <input className="p-2 m-2 flex-grow-1" placeholder={searchText} ref={searchInputEl}></input>
            {regexSearch && (
                <div className="p-2 m-2">
                    Regex search
                    <input type='checkbox' ref={regexCheckEl}></input>
                </div>

            )}
            <div className="p-2 m-2">{filterText}:</div>
            <select className="p-2 m-2" ref={filterSelectEl}>
                <option value={"NO_VALUE"}> </option>
                {filterValues.map((filter, index) => (
                    <option value={filter} key={filter}>{filter}</option>
                ))}

            </select>

            {dateSearch && (
                <div className="p-2 m-2">
                    Date
                    <input type='date' ref={dateEl}></input>
                </div>
            )}
            <button className="p-2 m-2" onClick={() => onSearch(searchInputEl.current.value, filterSelectEl.current.value, regexCheckEl.current.checked, dateEl.current.value)}>Search</button>
        </div>
    );

};
export default SearchFilterBar;