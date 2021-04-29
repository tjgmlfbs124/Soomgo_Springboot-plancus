var t = new Date();
var date = (t.getDate(), t.getMonth(), t.getFullYear(), new Date($.now()));
var events = [{
  title: "Hey!",
  start: new Date($.now() + 158e6),
  className: "bg-warning"
}, {
  title: "See John Deo",
  start: date,
  end: date,
  className: "bg-success"
}, {
  title: "Meet John Deo",
  start: new Date($.now() + 168e6),
  className: "bg-info"
}, {
  title: "Buy a Theme",
  start: new Date($.now() + 338e6),
  className: "bg-primary"
}];

var onDrop = function onDrop(droppable, date, calendar) {
  var eventObject = droppable.data('eventObject');
  var className = droppable.data('class');
  var options = $.extend({}, eventObject);
  options.start = date;

  if (className) {
    options.className = [className];
  }

  calendar.fullCalendar('renderEvent', options, true);

  if ($('#drop-remove').is(':checked')) {
    droppable.remove();
  }
};

var onEventClick = function onEventClick(event, jsEvent, view, calendar) {
  var modal = $(MODAL);
  var form = $("<form>\n      <label>Change event name</label>\n      <div class=\"input-group m-b-15\">\n        <input class=\"form-control\" type=\"text\" value=\"".concat(event.title, "\" />\n        <span class=\"input-group-append\">\n          <button type=\"submit\" class=\"btn btn-success\">\n            <i class=\"material-icons mr-2\">check</i> Save\n          </button>\n        </span>\n      </div>\n    </form>"));
  modal.modal('show');
  modal.find('.delete-event').show().end().find('.save-event').hide().end().find('.modal-body').empty().prepend(form).end().find('.delete-event').unbind('click').on('click', function () {
    calendar.fullCalendar('removeEvents', function (e) {
      return e._id == event._id;
    });
    modal.modal('hide');
  });
  modal.find('form').on('submit', function (e) {
    e.preventDefault();
    event.title = form.find('input[type=text]').val();
    calendar.fullCalendar('updateEvent', event);
    modal.modal('hide');
  });
};

var onSelect = function onSelect(start, end, jsEvent, calendar) {
  var modal = $(MODAL);
  modal.modal({
    backdrop: "static"
  });
  var form = $("<form>\n      <div class=\"row\">\n        <div class=\"col-12\">\n          <div class=\"form-group\">\n            <label class=\"control-label\">Event Name</label>\n            <input class=\"form-control\" placeholder=\"Insert Event Name\" type=\"text\" name=\"title\" />\n          </div>\n        </div>\n        <div class=\"col-12\">\n          <div class=\"form-group\">\n            <label class=\"control-label\">Category</label>\n            <select class=\"form-control custom-select\" name=\"category\">\n              <option value=\"bg-danger\">Danger</option>\n              <option value=\"bg-success\">Success</option>\n              <option value=\"bg-primary\">Primary</option>\n              <option value=\"bg-info\">Info</option>\n              <option value=\"bg-dark\">Dark</option>\n              <option value=\"bg-warning\">Warning</option>\n            </select>\n          </div>\n        </div>\n      </div>\n    </form>");
  modal.find('.delete-event').hide().end().find('.save-event').show().end().find('.modal-body').empty().prepend(form).end().find('.save-event').unbind('click').on('click', function () {
    form.submit();
  });
  modal.find('form').on('submit', function (e) {
    e.preventDefault();
    var title = form.find('input[name="title"]').val();
    var className = (form.find('input[name="beginning"]').val(), form.find('input[name="ending"]').val(), form.find('select[name="category"] option:checked').val());

    if (null !== title && 0 != title.length) {
      calendar.fullCalendar('renderEvent', {
        title: title,
        start: start,
        end: end,
        allDay: false,
        className: className
      }, true);
      return modal.modal('hide');
    }

    alert('You have to give a title to your event');
  });
  calendar.fullCalendar('unselect');
};

$('[data-toggle="fullcalendar"]').each(function () {
  var element = $(this);
  var options = {
    themeSystem: 'bootstrap4',
    closeButton: void 0 !== element.data('toastr-close-button') ? element.data('toastr-close-button') : false,
    slotDuration: '00:15:00',
    minTime: '08:00:00',
    maxTime: '19:00:00',
    defaultView: 'agendaWeek',
    handleWindowResize: true,
    height: $(window).height() - 200,
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'month,agendaWeek,agendaDay'
    },
    events: events,
    editable: true,
    droppable: true,
    eventLimit: true,
    selectable: true,
    drop: function drop(date) {
      onDrop($(this), date, element);
    },
    select: function select(start, end, jsEvent) {
      onSelect(start, end, jsEvent, element);
    },
    eventClick: function eventClick(event, jsEvent, view) {
      onEventClick(event, jsEvent, view, element);
    }
  };
  element.fullCalendar(options);
});
var MODAL = '#event-modal';
var EVENT = '#external-events div.external-event';
var EXT_EVENTS_CONTAINER = '#external-events';
var categoryForm = $('#add-category form');

var enableDrag = function enableDrag() {
  $(EVENT).each(function () {
    if ($(this).data('eventObject')) {
      return;
    }

    $(this).data('eventObject', {
      title: $.trim($(this).find('.external-event__title').text())
    });
    $(this).draggable({
      zIndex: 999,
      revert: true,
      revertDuration: 0
    });
  });
};

enableDrag();
$('.save-category').on('click', function () {
  var name = categoryForm.find('input[name="category-name"]').val();
  var color = categoryForm.find('select[name="category-color"]').val();

  if (null !== name && 0 != name.length) {
    $(EXT_EVENTS_CONTAINER).append("\n        <div class=\"external-event bg-".concat(color, "\" data-class=\"bg-").concat(color, "\">\n          <i class=\"mr-2 material-icons\">drag_handle</i>\n          <span class=\"external-event__title\">").concat(name, "</span>\n        </div>\n      "));
    enableDrag();
  }
});
