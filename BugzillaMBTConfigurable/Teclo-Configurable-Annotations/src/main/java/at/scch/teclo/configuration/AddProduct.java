/*#if ($AddProduct)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.Product;

public class AddProduct extends BugzillaConfig{

    private Product product;

    public AddProduct() {
        super("AddProduct");
        this.product = new Product("FooProduct");
        this.product.addComponent("BarComponent");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoAddProductPage();
        adapter.addAProduct(this.product, "BarComponent");
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.deleteProduct(this.product);
    }
}
/*#end*/