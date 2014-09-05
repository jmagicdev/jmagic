package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Geist of Saint Traft")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.CLERIC})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class GeistofSaintTraft extends Card
{
	public static final class GeistofSaintTraftAbility1 extends EventTriggeredAbility
	{
		public GeistofSaintTraftAbility1(GameState state)
		{
			super(state, "Whenever Geist of Saint Traft attacks, put a 4/4 white Angel creature token with flying onto the battlefield tapped and attacking. Exile that token at end of combat.");
			this.addPattern(whenThisAttacks());

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "put a 4/4 white Angel creature token with flying onto the battlefield tapped and attacking.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.ANGEL);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			token.setTappedAndAttacking(null);

			EventFactory putToken = token.getEventFactory();
			this.addEffect(putToken);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndOfCombatStepOf.instance(Players.instance()));

			EventFactory exile = exile(delayedTriggerContext(EffectResult.instance(putToken)), "Exile that token");

			EventFactory delayed = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile that token at end of combat.");
			delayed.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayed.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));
			delayed.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
			this.addEffect(delayed);
		}
	}

	public GeistofSaintTraft(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Whenever Geist of Saint Traft attacks, put a 4/4 white Angel creature
		// token with flying onto the battlefield tapped and attacking. Exile
		// that token at end of combat.
		this.addAbility(new GeistofSaintTraftAbility1(state));
	}
}
