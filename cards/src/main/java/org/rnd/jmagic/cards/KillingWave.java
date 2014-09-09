package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.PlayerInterface.ChooseParameters;
import org.rnd.jmagic.engine.generators.*;

@Name("Killing Wave")
@Types({Type.SORCERY})
@ManaCost("XB")
@ColorIdentity({Color.BLACK})
public final class KillingWave extends Card
{
	public static PlayerInterface.ChooseReason KILLING_WAVE_REASON = new PlayerInterface.ChooseReason("KillingWave", "Pay X life?", true);

	/**
	 * @eparam NUMBER: X
	 * @eparam RESULT: empty
	 */
	public static final EventType KILLING_WAVE = new EventType("KILLING_WAVE")
	{
		@Override
		public Parameter affects()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Player, java.util.Collection<GameObject>> creatures = new java.util.HashMap<Player, java.util.Collection<GameObject>>();
			for(Player p: game.actualState.players)
				creatures.put(p, new java.util.LinkedList<GameObject>());
			for(GameObject o: game.actualState.battlefield().objects)
				if(o.getTypes().contains(Type.CREATURE))
					creatures.get(o.getController(game.actualState)).add(o);

			// keys are creatures, values are whether the player chose to pay
			// life
			java.util.Map<GameObject, EventFactory> choices = new java.util.HashMap<GameObject, EventFactory>();
			int X = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			for(Player controller: game.actualState.apnapOrder(Set.fromCollection(game.actualState.players)))
			{
				Event payLife = payLife(Identity.instance(controller), X, "Pay " + X + " life").createEvent(game, event.getSource());
				int lifePaymentSoFar = 0;
				for(GameObject creature: creatures.get(controller))
				{
					if(!payLife.attempt(event))
					{
						choices.put(creature, sacrificeSpecificPermanents(Identity.instance(controller), Identity.instance(creature), "Sacrifice" + creature));
						break;
					}

					if(lifePaymentSoFar + X > controller.lifeTotal)
					{
						choices.put(creature, sacrificeSpecificPermanents(Identity.instance(controller), Identity.instance(creature), "Sacrifice" + creature));
						break;
					}

					ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, KILLING_WAVE_REASON);
					chooseParameters.whatForID = creature.ID;
					Answer a = controller.choose(chooseParameters).get(0);
					if(a == Answer.YES)
					{
						lifePaymentSoFar += X;
						choices.put(creature, payLife(Identity.instance(controller), X, "Pay " + X + " life"));
					}
					else
						choices.put(creature, sacrificeSpecificPermanents(Identity.instance(controller), Identity.instance(creature), "Sacrifice" + creature));
				}
			}

			EventFactory go = simultaneous("For each creature, its controller sacrifices it unless he or she pays X life.", choices.values().toArray(new EventFactory[] {}));
			go.createEvent(game, event.getSource()).perform(event, false);

			event.setResult(Empty.set);
			return true;
		}

	};

	public KillingWave(GameState state)
	{
		super(state);

		// For each creature, its controller sacrifices it unless he or she pays
		// X life.
		EventFactory killingWave = new EventFactory(KILLING_WAVE, "For each creature, its controller sacrifices it unless he or she pays X life.");
		killingWave.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		this.addEffect(killingWave);
	}
}
