
function getParameter(pName){
    let parameters=new URLSearchParams(window.location.search);
    return parameters.get(pName);
}