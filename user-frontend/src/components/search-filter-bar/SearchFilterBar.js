import React from "react";

const SearchFilterBar = ({ searchText, filterText, filterValues }) => {

    return (
        <div className="d-flex">
            <input className="p-2 m-2 flex-grow-1" placeholder={searchText}></input>
            <div className="p-2 m-2">{filterText}:</div>
            <select className="p-2 m-2">
                <option value={"NO_VALUE"}> </option>
                {filterValues.map((filter, index) => (
                    <option value={filter} key={filter}>{filter}</option>
                ))}

            </select>
            <button className="p-2 m-2">Search</button>
        </div>
    );

};
export default SearchFilterBar;