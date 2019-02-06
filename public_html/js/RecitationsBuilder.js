function buildRecitations() {   
    initBannerOfSyllabus();
    var dataFile = "./js/RecitationsData.json";
    loadData(dataFile, addRecitations);
}

function initBannerOfSyllabus() {
    
    var courseDataFile = "./js/CourseInformationData.json";
    $.getJSON(courseDataFile, function (json) {
        var banner = $("#banner");
        var subject = json.course_subject;
        var number = json.course_number;
        var semester = json.course_semester;
        var year = json.course_year;
        var title = json.course_title;
        banner.append(subject + " "+ number + " - " + semester + " " + year + "\n" + title);
    });
}

function addRecitations(data) {
    var recTable = $("#rec_table");
    var rowParity = 0;
    for (var i = 0; i < data.recitations.length; i+=2) {
        var text = "<tr>";
        var rec = data.recitations[i];
        var cellParity = rowParity;
        text += buildRecCell(cellParity, rec);
        cellParity++;
        cellParity %= 2;
        if ((i+1) < data.recitations.length) {
            rec = data.recitations[i+1];
            text += buildRecCell(cellParity, rec);
        }
        else
            text += "<td></td>";
        text += "</tr>";
        recTable.append(text);
        rowParity++;
        rowParity %= 2;
    }
}
function buildRecCell(recClassNum, recData) {
    var text = "<td class='rec_" + recClassNum + "'>"
                + "<table><tr><td valign='top' class='rec_cell'>" 
                + recData.section + "<br />"
                + recData.day_time + "<br />"
                + recData.location + "<br /></td></tr>"
                + "<tr>";
    
    // RECITATION TA #1
    text += "<td class='ta_cell'><strong>Supervising TA</strong><br />";
    if (recData.ta_1 != "none")
        text += "<img src='./images/tas/" 
            + recData.ta_1.replace(/\s/g, '')
            + ".JPG' width='100' height='100' />"
            + "<br clear='both' />"
            + "(" + recData.ta_1 + ")<br />";
    else
        text += "TBA";
    text += "</td>";
    
    // RECITATION TA #2
    text += "<td class='ta_cell'><strong>Supervising TA</strong><br />";
    if (recData.ta_2 != "none")
        text += "<img src='./images/tas/" 
            + recData.ta_2.replace(/\s/g, '')
            + ".JPG' width='100' height='100' />"
            + "<br clear='both' />"
            + "(" + recData.ta_2 + ")<br />";            
    else
        text += "TBA";
    text += "</table></td>";
    return text;
}

