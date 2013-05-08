package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import java.util.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.Set;
import org.rnd.jmagic.engine.generators.*;

@Name("Grindstone")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({})
public final class Grindstone extends Card
{
	public static final EventType GRINDSTONE_EVENT = new EventType("GRINDSTONE_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, Map<Parameter, Set> parameters)
		{
			GameObject ability = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			Player target = parameters.get(Parameter.PLAYER).getOne(Player.class);

			boolean repeat = true;
			while(repeat)
			{
				Event mill = millCards(Identity.instance(target), 2, "Put the top two cards of target player's library into that player's graveyard.").createEvent(game, ability);
				mill.perform(event, true);

				Set result = mill.getResult();
				if(result.size() > 1)
				{
					java.util.Set<Color> colors = Color.allColors();
					for(ZoneChange zc: result.getAll(ZoneChange.class))
						colors.retainAll(game.actualState.<GameObject>get(zc.newObjectID).getColors());
					repeat = !colors.isEmpty();
				}
				else
					repeat = false;
			}
			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class GrindstoneAbility0 extends ActivatedAbility
	{
		public GrindstoneAbility0(GameState state)
		{
			super(state, "(3), (T): Put the top two cards of target player's library into that player's graveyard. If both cards share a color, repeat this process.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory effect = new EventFactory(GRINDSTONE_EVENT, "Put the top two cards of target player's library into that player's graveyard. If both cards share a color, repeat this process.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, target);
			this.addEffect(effect);
		}
	}

	public Grindstone(GameState state)
	{
		super(state);

		// (3), (T): Put the top two cards of target player's library into that
		// player's graveyard. If both cards share a color, repeat this process.
		this.addAbility(new GrindstoneAbility0(state));
	}
}
