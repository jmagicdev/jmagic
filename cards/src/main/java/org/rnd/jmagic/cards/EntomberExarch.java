package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Entomber Exarch")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class EntomberExarch extends Card
{
	public static final class EntomberExarchAbility0 extends EventTriggeredAbility
	{
		public EntomberExarchAbility0(GameState state)
		{
			super(state, "When Entomber Exarch enters the battlefield, choose one \u2014\n\u2022 Return target creature card from your graveyard to your hand.\n\u2022 Target opponent reveals his or her hand, you choose a noncreature card from it, then that player discards that card.");
			this.addPattern(whenThisEntersTheBattlefield());

			// Return target creature card from your graveyard to your hand
			{
				SetGenerator yourYard = GraveyardOf.instance(You.instance());
				SetGenerator target = targetedBy(this.addTarget(1, Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourYard)), "target creature card in your graveyard"));

				EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
				move.parameters.put(EventType.Parameter.CAUSE, This.instance());
				move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
				move.parameters.put(EventType.Parameter.OBJECT, target);
				this.addEffect(1, move);
			}

			// target opponent reveals his or her hand, you choose a noncreature
			// card from it, then that player discards that card
			{
				SetGenerator target = targetedBy(this.addTarget(2, OpponentsOf.instance(You.instance()), "target opponent"));

				SetGenerator cards = InZone.instance(HandOf.instance(target));
				EventType.ParameterMap revealParameters = new EventType.ParameterMap();
				revealParameters.put(EventType.Parameter.CAUSE, This.instance());
				revealParameters.put(EventType.Parameter.OBJECT, cards);
				this.addEffect(2, new EventFactory(EventType.REVEAL, revealParameters, "Target opponent reveals his or her hand."));

				SetGenerator choices = RelativeComplement.instance(cards, HasType.instance(Type.CREATURE));

				EventType.ParameterMap parameters = new EventType.ParameterMap();
				parameters.put(EventType.Parameter.CAUSE, This.instance());
				parameters.put(EventType.Parameter.PLAYER, You.instance());
				parameters.put(EventType.Parameter.TARGET, target);
				parameters.put(EventType.Parameter.CHOICE, Identity.instance(choices));
				this.addEffect(2, new EventFactory(EventType.DISCARD_FORCE, parameters, "You choose a noncreature card from it, then that player discards that card."));
			}
		}
	}

	public EntomberExarch(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Entomber Exarch enters the battlefield, choose one \u2014 Return
		// target creature card from your graveyard to your hand; or target
		// opponent reveals his or her hand, you choose a noncreature card from
		// it, then that player discards that card.
		this.addAbility(new EntomberExarchAbility0(state));
	}
}
