package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrambleverse")
@Types({Type.SORCERY})
@ManaCost("6RR")
@ColorIdentity({Color.RED})
public final class Scrambleverse extends Card
{
	public static final EventType SCRAMBLE = new EventType("SCRAMBLE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = new Set(event.getSource());

			java.util.Random rnd = new java.util.Random();
			java.util.List<Player> players = new java.util.ArrayList<Player>(game.actualState.players);
			java.util.Iterator<Player> i = players.iterator();
			while(i.hasNext())
				if(i.next().outOfGame)
					i.remove();

			Set nonlandPermanents = parameters.get(Parameter.OBJECT);
			for(GameObject o: nonlandPermanents.getAll(GameObject.class))
			{
				Player p = players.get(rnd.nextInt(players.size()));

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(o));
				part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(p));

				java.util.Map<Parameter, Set> effectParameters = new java.util.HashMap<Parameter, Set>();
				effectParameters.put(Parameter.CAUSE, cause);
				effectParameters.put(Parameter.EFFECT, new Set(part));
				effectParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
				Event effect = createEvent(game, p + " gains control of " + o, EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectParameters);
				effect.perform(event, false);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public Scrambleverse(GameState state)
	{
		super(state);

		// For each nonland permanent, choose a player at random. Then each
		// player gains control of each permanent for which he or she was
		// chosen. Untap those permanents.
		EventFactory scramble = new EventFactory(SCRAMBLE, "For each nonland permanent, choose a player at random. Then each player gains control of each permanent for which he or she was chosen. Untap those permanents.");
		scramble.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)));
		this.addEffect(scramble);
	}
}
