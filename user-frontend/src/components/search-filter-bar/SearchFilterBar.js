import React from "react";
import { useRef } from "react";

const SearchFilterBar = ({ searchText, filterText, filterValues, onSearch }) => {

    const searchInputEl = useRef("");
    const filterSelectEl = useRef("NO_VALUE");

    return (
        <div className="d-flex">
            <input className="p-2 m-2 flex-grow-1" placeholder={searchText} ref={searchInputEl}></input>
            <div className="p-2 m-2">{filterText}:</div>
            <select className="p-2 m-2" ref={filterSelectEl}>
                <option value={"NO_VALUE"}> </option>
                {filterValues.map((filter, index) => (
                    <option value={filter} key={filter}>{filter}</option>
                ))}

            </select>
            <button className="p-2 m-2" onClick={() => onSearch(searchInputEl.current.value, filterSelectEl.current.value)}>Search</button>
        </div>
    );

};
export default SearchFilterBar;