package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Golgari Grave-Troll")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL, SubType.SKELETON})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class GolgariGraveTroll extends Card
{
	public static final class Regeneration extends ActivatedAbility
	{
		public Regeneration(GameState state)
		{
			super(state, "(1), Remove a +1/+1 counter from Golgari Grave-Troll: Regenerate Golgari Grave-Troll.");
			this.setManaCost(new ManaPool("1"));

			this.addCost(removeCountersFromThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Golgari Grave-Troll"));

			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Golgari Grave-Troll"));
		}
	}

	public GolgariGraveTroll(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Golgari Grave-Troll enters the battlefield with a +1/+1 counter on it
		// for each creature card in your graveyard.
		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		SetGenerator creaturesInYourGraveyard = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourGraveyard));
		SetGenerator numCounters = Count.instance(creaturesInYourGraveyard);
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), numCounters, "a +1/+1 counter on it for each creature card in your graveyard", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// (1), Remove a +1/+1 counter from Golgari Grave-Troll: Regenerate
		// Golgari Grave-Troll.
		this.addAbility(new Regeneration(state));

		// Dredge 6 (If you would draw a card, instead you may put exactly six
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 6));
	}
}
