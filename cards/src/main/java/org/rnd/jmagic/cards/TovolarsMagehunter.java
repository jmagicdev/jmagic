package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tovolar's Magehunter")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TovolarsMagehunter extends AlternateCard
{
	public static final class TovolarsMagehunterAbility0 extends EventTriggeredAbility
	{
		public TovolarsMagehunterAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, Tovolar's Magehunter deals 2 damage to that player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(permanentDealDamage(2, thatPlayer, "Tovolar's Magehunter deals 2 damage to that player."));
		}
	}

	public TovolarsMagehunter(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.setColorIndicator(Color.RED);

		// Whenever an opponent casts a spell, Tovolar's Magehunter deals 2
		// damage to that player.
		this.addAbility(new TovolarsMagehunterAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Tovolar's Magehunter.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
