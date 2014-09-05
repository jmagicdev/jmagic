package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lashwrithe")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Lashwrithe extends Card
{
	public static final class LashwritheAbility1 extends StaticAbility
	{
		public LashwritheAbility1(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 for each Swamp you control.");

			SetGenerator num = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), num, num));
		}
	}

	public Lashwrithe(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +1/+1 for each Swamp you control.
		this.addAbility(new LashwritheAbility1(state));

		// Equip (b/p)(b/p) ((b/p) can be paid with either (B) or 2 life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(b/p)(b/p)"));
	}
}
