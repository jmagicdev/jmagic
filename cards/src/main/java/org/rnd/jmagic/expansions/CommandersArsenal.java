package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Commander's Arsenal")
public final class CommandersArsenal extends SimpleExpansion
{
	public CommandersArsenal()
	{
		super();

		this.addCards(Rarity.COMMON, "Command Tower", "Rhystic Study");
		this.addCards(Rarity.UNCOMMON, "Loyal Retainers");
		this.addCards(Rarity.RARE, "Chaos Warp", "Decree of Pain", "Desertion", "Diaochan, Artful Beauty", "Dragonlair Spider", "Duplicant", "Edric, Spymaster of Trest", "Mind's Eye", "Mirari's Wake", "Scroll Rack", "Sylvan Library");
		this.addCards(Rarity.MYTHIC, "Kaalia of the Vast", "Maelstrom Wanderer", "The Mimeoplasm", "Vela the Night-Clad");
	}
}
