package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Greatsword")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Greatsword extends Card
{
	public static final class GreatswordAbility0 extends StaticAbility
	{
		public GreatswordAbility0(GameState state)
		{
			super(state, "Equipped creature gets +3/+0.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +3, +0));
		}
	}

	public Greatsword(GameState state)
	{
		super(state);

		// Equipped creature gets +3/+0.
		this.addAbility(new GreatswordAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
