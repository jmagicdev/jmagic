package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonmail Hauberk")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DemonmailHauberk extends Card
{
	public static final class DemonmailHauberkAbility0 extends StaticAbility
	{
		public DemonmailHauberkAbility0(GameState state)
		{
			super(state, "Equipped creature gets +4/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +4, +2));
		}
	}

	public DemonmailHauberk(GameState state)
	{
		super(state);

		// Equipped creature gets +4/+2.
		this.addAbility(new DemonmailHauberkAbility0(state));

		// Equip\u2014Sacrifice a creature.
		EventFactory sacrifice = sacrifice(You.instance(), 1, CreaturePermanents.instance(), "Sacrifice a creature");
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, new CostCollection(org.rnd.jmagic.abilities.keywords.Equip.COST_TYPE, sacrifice)));
	}
}
