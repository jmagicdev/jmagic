package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grafdigger's Cage")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class GrafdiggersCage extends Card
{
	public static final class GrafdiggersCageAbility0 extends StaticAbility
	{
		public GrafdiggersCageAbility0(GameState state)
		{
			super(state, "Creature cards can't enter the battlefield from graveyards or libraries.");

			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Union.instance(GraveyardOf.instance(Players.instance()), LibraryOf.instance(Players.instance())), Battlefield.instance(), HasType.instance(Type.CREATURE), true);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public static final class GrafdiggersCageAbility1 extends StaticAbility
	{
		public GrafdiggersCageAbility1(GameState state)
		{
			super(state, "Players can't cast cards in graveyards or libraries.");

			MultipleSetPattern nonartifactSpells = new MultipleSetPattern(true);
			nonartifactSpells.addPattern(new SimpleSetPattern(InZone.instance(Union.instance(GraveyardOf.instance(Players.instance()), LibraryOf.instance(Players.instance())))));
			nonartifactSpells.addPattern(SetPattern.CASTABLE);

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, Players.instance());
			castSpell.put(EventType.Parameter.OBJECT, nonartifactSpells);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);
		}
	}

	public GrafdiggersCage(GameState state)
	{
		super(state);

		// Creature cards can't enter the battlefield from graveyards or
		// libraries.
		this.addAbility(new GrafdiggersCageAbility0(state));

		// Players can't cast cards in graveyards or libraries.
		this.addAbility(new GrafdiggersCageAbility1(state));
	}
}
