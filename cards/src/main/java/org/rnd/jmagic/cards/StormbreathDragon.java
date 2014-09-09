package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Stormbreath Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class StormbreathDragon extends Card
{
	public static class MonstrousRoar extends EventTriggeredAbility
	{
		public MonstrousRoar(GameState state)
		{
			super(state, "When Stormbreath Dragon becomes monstrous, it deals damage to each opponent equal to the number of cards in that player's hand.");
			
			this.addPattern(whenThisBecomesMonstrous());
			
			DynamicEvaluation eachOpponent = DynamicEvaluation.instance();

			SetGenerator handSize = Count.instance(InZone.instance(HandOf.instance(eachOpponent)));
			EventFactory dealDamage = permanentDealDamage(handSize, eachOpponent, "Stormbreath Dragon deals damage to one opponent equal to the number of cards in that player's hand.");

			EventFactory damageEachOpponent = new EventFactory(FOR_EACH_PLAYER, "Stormbreath Dragon deals damage to each opponent equal to the number of cards in that player's hand.");
			damageEachOpponent.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			damageEachOpponent.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachOpponent));
			damageEachOpponent.parameters.put(EventType.Parameter.EFFECT, Identity.instance(dealDamage));
			this.addEffect(damageEachOpponent);
		}
	}

	public StormbreathDragon(GameState state)
	{
		super(state);
		
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromWhite(state));

		this.addAbility(new org.rnd.jmagic.abilities.Monstrosity(state, "(5)(R)(R)", 3));

		this.addAbility(new MonstrousRoar(state));

		this.setPower(4);
		this.setToughness(4);
	}
}
