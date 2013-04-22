package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harvester of Souls")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HarvesterofSouls extends Card
{
	public static final class HarvesterofSoulsAbility1 extends EventTriggeredAbility
	{
		public HarvesterofSoulsAbility1(GameState state)
		{
			super(state, "Whenever another nontoken creature dies, you may draw a card.");
			SetGenerator nonToken = RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance());
			SetGenerator anotherNonToken = RelativeComplement.instance(nonToken, ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(anotherNonToken));

			this.addEffect(youMay(drawACard()));
		}
	}

	public HarvesterofSouls(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Whenever another nontoken creature dies, you may draw a card.
		this.addAbility(new HarvesterofSoulsAbility1(state));
	}
}
