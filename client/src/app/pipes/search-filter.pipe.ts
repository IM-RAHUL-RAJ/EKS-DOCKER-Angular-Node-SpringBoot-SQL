import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(items: any[], searchText: string): any[] {
    if (!items) {
      return [];
    }
    if (!searchText) {
      return items;
    }
    searchText = searchText.toLocaleLowerCase();


    return items.filter(it => {
      return (it.direction.toLocaleLowerCase().includes(searchText)||it.instrumentId.toLocaleLowerCase().includes(searchText)||it.order.orderDate.toLocaleLowerCase().includes(searchText)||it.tradeDate.toLocaleLowerCase().includes(searchText));
    });
  }

}
