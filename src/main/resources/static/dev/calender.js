class MyCalender{
  constructor(){
    this.events = [];
    this.options = null;
  }

  setOption(target, period, eventCallback){
    var element = $(target);
    console.log("element : " , element);

    this.options = {
      themeSystem: 'bootstrap4',
      closeButton: void 0 !== element.data('toastr-close-button') ? element.data('toastr-close-button') : false,
      slotDuration: '00:45:00',
      minTime: '08:00:00',
      maxTime: '19:00:00',
      defaultView: period,
      handleWindowResize: true,
      height: $(window).height() - 50,
      contentHeight:550,
      header: {
        left: 'prev,next',
        center: 'title',
        right: ''
      },
      events: this.events,
      editable: false,
      droppable: false,
      eventLimit: true,
      selectable: false,
      monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
      monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
      dayNames: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
      dayNamesShort: ['SUN','MON','TUE','WED','THU','FRI','SAT'],
      drop: function drop(date) {
        // onDrop($(this), date, element);
      },
      select: function select(start, end, jsEvent) {
        // onSelect(start, end, jsEvent, element);
      },
      eventClick: function eventClick(event, jsEvent, view) {
        if(eventCallback)
          eventCallback(event, jsEvent, view, element);
      }
    };
    this.drawCalendar(element, this.options);

  }

  drawCalendar(target, options){
    console.log("target : ", target);
    target.fullCalendar(options);
    this.setButtonStyle();
  }

  setButtonStyle(){
    $(".fc-left").css("width","100%");
    $(".btn-group").css("width","100%");

    $(".fc-prev-button").css("max-width","50px");
    $(".fc-prev-button").css("position","absolute");

    $(".fc-next-button").css("max-width","50px");
    $(".fc-next-button").css("position","absolute");
    $(".fc-next-button").css("right","0");

    $(".fc-center > h2").css("font-size","16px");
    $(".fc-center > h2").css("font-family","noto_bold");
    $(".fc-center > h2").css("line-height","1px");

    $(".btn-primary").css("background-color","#FAFBFE");
    $(".btn-primary").css("border-color","#FAFBFE");
    $(".btn-primary").css("box-shadow","none");
    $(".btn-primary > span").css("color","#2E9CDB");

    var slatHeight = $("#calendar").find('.fc-slats').height();
    var height = $('#calendar').find('.fc-time-grid-container').height();
    if(slatHeight < height)
    {
        $('#calendar').find('.fc-time-grid-container').css('max-height',slatHeight+'px');
    }

  }

}
