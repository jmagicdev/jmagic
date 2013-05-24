package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dormant Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("2UG")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class DormantSliver extends Card
{
	// TODO : Awkward.... The quotes around this are so that the text is quoted
	// correctly in the name of the static ability that adds this triggered
	// ability. Figure out what to do about this.
	@Name("\"When this permanent enters the battlefield, draw a card.\"")
	public static final class SliverDraw extends EventTriggeredAbility
	{
		public SliverDraw(GameState state)
		{
			super(state, "When this permanent enters the battlefield, draw a card.");

			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(drawACard());
		}
	}

	public DormantSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All Sliver creatures have defender.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, org.rnd.jmagic.abilities.keywords.Defender.class, "All Sliver creatures have defender."));

		// All Slivers have
		// "When this permanent enters the battlefield, draw a card."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, SliverDraw.class, "All Slivers have \"When this permanent enters the battlefield, draw a card.\""));
	}
}
