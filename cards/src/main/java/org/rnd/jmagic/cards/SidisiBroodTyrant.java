package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sidisi, Brood Tyrant")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.NAGA})
@ManaCost("1BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class SidisiBroodTyrant extends Card
{
	public static final class SidisiBroodTyrantAbility0 extends EventTriggeredAbility
	{
		public SidisiBroodTyrantAbility0(GameState state)
		{
			super(state, "Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());
			this.addEffect(millCards(You.instance(), 3, "Put the top three cards of your library into your graveyard."));
		}
	}

	public static final class ThingsBeingMilledPattern implements SetPattern
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			int library = you.getLibraryID();
			int graveyard = you.getGraveyardID();
			for(ZoneChange zc: set.getAll(ZoneChange.class))
			{
				if(zc.sourceZoneID == library && zc.destinationZoneID == graveyard && state.<GameObject>get(zc.newObjectID).getTypes().contains(Type.CREATURE))
					return true;
			}
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// nothing to do
		}
	}

	public static final class SidisiBroodTyrantAbility1 extends EventTriggeredAbility
	{
		public SidisiBroodTyrantAbility1(GameState state)
		{
			super(state, "Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.");
			// this doesn't use a zone change pattern, because zone change
			// patterns only match single zone changes.

			// so, i'm matching on MOVE_BATCH.

			// ... don't do this if you can help it.

			SimpleEventPattern delveThings = new SimpleEventPattern(EventType.MOVE_BATCH);
			delveThings.put(EventType.Parameter.TARGET, new ThingsBeingMilledPattern());
			this.addPattern(delveThings);

			CreateTokensFactory zombie = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			zombie.setColors(Color.BLACK);
			zombie.setSubTypes(SubType.ZOMBIE);
			this.addEffect(zombie.getEventFactory());
		}
	}

	public SidisiBroodTyrant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put
		// the top three cards of your library into your graveyard.
		this.addAbility(new SidisiBroodTyrantAbility0(state));

		// Whenever one or more creature cards are put into your graveyard from
		// your library, put a 2/2 black Zombie creature token onto the
		// battlefield.
		this.addAbility(new SidisiBroodTyrantAbility1(state));
	}
}
