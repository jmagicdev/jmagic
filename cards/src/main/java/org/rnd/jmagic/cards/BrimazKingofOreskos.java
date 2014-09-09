package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Brimaz, King of Oreskos")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SOLDIER})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class BrimazKingofOreskos extends Card
{
	public static final class BrimazKingofOreskosAbility1 extends EventTriggeredAbility
	{
		public BrimazKingofOreskosAbility1(GameState state)
		{
			super(state, "Whenever Brimaz, King of Oreskos attacks, put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield attacking.");
			this.addPattern(whenThisAttacks());

			CreateTokensFactory cat = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield attacking.");
			cat.setColors(Color.WHITE);
			cat.setSubTypes(SubType.CAT, SubType.SOLDIER);
			cat.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			cat.setAttacking(null);
			this.addEffect(cat.getEventFactory());
		}
	}

	public static final class BrimazKingofOreskosAbility2 extends EventTriggeredAbility
	{
		public BrimazKingofOreskosAbility2(GameState state)
		{
			super(state, "Whenever Brimaz blocks a creature, put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield blocking that creature.");

			SimpleEventPattern blocksACreature = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			blocksACreature.put(EventType.Parameter.DEFENDER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(blocksACreature);

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.ATTACKER);

			CreateTokensFactory cat = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield attacking.");
			cat.setColors(Color.WHITE);
			cat.setSubTypes(SubType.CAT, SubType.SOLDIER);
			cat.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			cat.setBlocking(thatCreature);
			this.addEffect(cat.getEventFactory());
		}
	}

	public BrimazKingofOreskos(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Whenever Brimaz, King of Oreskos attacks, put a 1/1 white Cat Soldier
		// creature token with vigilance onto the battlefield attacking.
		this.addAbility(new BrimazKingofOreskosAbility1(state));

		// Whenever Brimaz blocks a creature, put a 1/1 white Cat Soldier
		// creature token with vigilance onto the battlefield blocking that
		// creature.
		this.addAbility(new BrimazKingofOreskosAbility2(state));
	}
}
