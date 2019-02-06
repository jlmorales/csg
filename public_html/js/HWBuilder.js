// DATA TO LOAD
var hws;
var daysOfWeek;
var redInc;
var greenInc;
var blueInc;

function HW(hDate, hTime, hTitle, hTopic, hLink, hCriteria) {
    this.date = hDate;
    this.time = hTime;
    this.title = hTitle;
    this.topic = hTopic;
    this.link = hLink;
    this.criteria = hCriteria;
}
function ScheduleDate(sMonth, sDay) {
    this.month = sMonth;
    this.day = sDay;
}

function initBanner() {

    var courseDataFile = "./js/CourseInformationData.json";
    $.getJSON(courseDataFile, function (json) {
        var banner = $("#banner");
        var subject = json.course_subject;
        var number = json.course_number;
        var semester = json.course_semester;
        var year = json.course_year;
        var title = json.course_title;
        banner.append(subject + " " + number + " - " + semester + " " + year + "\n" + title);
    });
}

function initHWs() {
    initBanner();

    redInc = 10;
    greenInc = 10;
    blueInc = 5;

    daysOfWeek = new Array(7);
    daysOfWeek[0] = "Sunday";
    daysOfWeek[1] = "Monday";
    daysOfWeek[2] = "Tuesday";
    daysOfWeek[3] = "Wednesday";
    daysOfWeek[4] = "Thursday";
    daysOfWeek[5] = "Friday";
    daysOfWeek[6] = "Saturday";

    var dataFile = "./js/ScheduleData.json";
    loadData(dataFile);
    
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        loadJSONData(json);
        addHWs();
    });
}
function loadJSONData(data) {
    // LOAD HWs DATA
    hws = new Array();
    for (var i = 0; i < data.hws.length; i++) {
        var hwData = data.hws[i];
        var hwDate = new ScheduleDate(hwData.month, hwData.day);
        var hw = new HW(hwDate, hwData.time, hwData.title, hwData.topic, hwData.link, hwData.criteria);
        hws[i] = hw;
    }
}

function addHWs() {
    var tBody = $("#hws");
    var red = 240;
    var green = 240;
    var blue = 255;
    for (var i = 0; i < hws.length; i++) {
        var hw = hws[i];
        var day = hw.date.day;
        var month = hw.date.month;
        var dayOfWeek = getDayOfWeek(day, month);

        // THE FIRST CELL
        var textToAppend = "<tr class=\"hw\" style=\"background-color:rgb(" + red + "," + green + "," + blue + ")\">"
                + "<td class=\"hw\" style=\"padding-right: 60px\">"
                + "<br />";
        if (hw.link.valueOf() === "none".valueOf()) {
            textToAppend += hw.title;
        } else {
            textToAppend += "<a href=\"" + hw.link + "\">" + hw.title + "</a>";
        }
        textToAppend += " - " + hw.topic + "<br /><br /></td>";

        // THE SECOND CELL
        textToAppend += "<td class=\"hw\" style=\"padding-right: 60px\">"
                + "<br />" + dayOfWeek + ", " + month + "/" + day
                + "<br /><br /><br />"
                + "</td>";
        if (hw.criteria.valueOf() === "none".valueOf()) {
            textToAppend += "<td class=\"hw\" style=\"padding-right: 60px\"><br />TBA<br /><br /><br /></td>";
        } else {
            textToAppend += "<td class=\"hw\" style=\"padding-right: 60px\">"
                    + "<a href=\"" + hw.criteria + "\"><br />" + hw.title + " Grading Criteria</a><br /><br /><br /></td>";
        }

        textToAppend += "</tr>";
        tBody.append(textToAppend);
        red -= redInc;
        green -= greenInc;
        blue -= blueInc;
    }
}
function getDayOfWeek(gDay, gMonth) {
    var date = new Date();
    date.setDate(1);
    date.setMonth(gMonth - 1);
    date.setDate(gDay);
    return daysOfWeek[date.getDay()];
}

function initBannerImage() {
    var courseDataFile = "./js/CourseInformationData.json";
    
    $.get(courseDataFile, function (json) {
        var bannerImage = $("#bannerImage");
        bannerImage.attr("src", json.banner_image);
    });
}

function initLeftFooter() {
    var courseDataFile = "./js/CourseInformationData.json";
    $.get(courseDataFile, function (json) {
        var leftFooter = $("#leftFooter");
        leftFooter.attr("src", json.left_footer);
    });
}

function initRightFooter() {
    var courseDataFile = "./js/CourseInformationData.json";
    $.get(courseDataFile, function(json) {
        var rightFooter = $("#rightFooter");
        rightFooter.attr("src", json.right_footer);
    });
}