import React from "react";

const SearchFilterBar = ({ searchText, filterText, filterValues }) => {

    return (
        <div className="d-flex bd-highlight example-parent">
            <input className="p-2 flex-grow-1 bd-highlight col-example" placeholder={searchText}></input>
            <div className="p-2 bd-highlight col-example">{filterText}:</div>
            <select className="p-2 bd-highlight col-example">
                <option value={"NO_VALUE"}> </option>
                {filterValues.map((filter, index) => (
                    <option value={filter} key={filter}>{filter}</option>
                ))}

            </select>
            <button className="p-2 bd-highlight col-example">Search</button>
        </div>
    );

};
export default SearchFilterBar;