package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghoulraiser")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Ghoulraiser extends Card
{
	public static final class GhoulraiserAbility0 extends EventTriggeredAbility
	{
		public GhoulraiserAbility0(GameState state)
		{
			super(state, "When Ghoulraiser enters the battlefield, return a Zombie card at random from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());

			SetGenerator zombieCardsInYourGraveyard = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), Cards.instance(), InZone.instance(yourGraveyard));
			EventFactory randomZombie = random(zombieCardsInYourGraveyard, "Determine a random Zombie card in your graveyard");
			this.addEffect(randomZombie);

			EventFactory ret = new EventFactory(EventType.MOVE_OBJECTS, "Return that card from your graveyard to your hand");
			ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
			ret.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			ret.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(randomZombie));
			this.addEffect(ret);
		}
	}

	public Ghoulraiser(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Ghoulraiser enters the battlefield, return a Zombie card at
		// random from your graveyard to your hand.
		this.addAbility(new GhoulraiserAbility0(state));
	}
}
