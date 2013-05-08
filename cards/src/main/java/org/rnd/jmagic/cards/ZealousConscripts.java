package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zealous Conscripts")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ZealousConscripts extends Card
{
	public static final class ZealousConscriptsAbility1 extends EventTriggeredAbility
	{
		public ZealousConscriptsAbility1(GameState state)
		{
			super(state, "When Zealous Conscripts enters the battlefield, gain control of target permanent until end of turn. Untap that permanent. It gains haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target permanent until end of turn.", controlPart));

			this.addEffect(untap(target, "Untap that permament."));

			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
		}
	}

	public ZealousConscripts(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Zealous Conscripts enters the battlefield, gain control of
		// target permanent until end of turn. Untap that permanent. It gains
		// haste until end of turn.
		this.addAbility(new ZealousConscriptsAbility1(state));
	}
}
