package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Runechanter's Pike")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({})
public final class RunechantersPike extends Card
{
	public static final class RunechantersPikeAbility0 extends StaticAbility
	{
		public RunechantersPikeAbility0(GameState state)
		{
			super(state, "Equipped creature has first strike and gets +X/+0, where X is the number of instant and sorcery cards in your graveyard.");

			SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))));
			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
			this.addEffectPart(modifyPowerAndToughness(equipped, number, numberGenerator(0)));
		}
	}

	public RunechantersPike(GameState state)
	{
		super(state);

		// Equipped creature has first strike and gets +X/+0, where X is the
		// number of instant and sorcery cards in your graveyard.
		this.addAbility(new RunechantersPikeAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
