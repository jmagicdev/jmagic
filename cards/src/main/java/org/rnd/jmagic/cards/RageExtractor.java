package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rage Extractor")
@Types({Type.ARTIFACT})
@ManaCost("4(R/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RageExtractor extends Card
{
	public static final class RageExtractorAbility0 extends EventTriggeredAbility
	{
		public RageExtractorAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell with (p) in its mana cost, Rage Extractor deals damage equal to that spell's converted mana cost to target creature or player.");

			ManaSymbol phyrexian = new ManaSymbol("(P)");
			phyrexian.isPhyrexian = true;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasInManaCost.instance(phyrexian)));
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			SetGenerator spell = EventResult.instance(TriggerEvent.instance(This.instance()));
			SetGenerator amount = ConvertedManaCostOf.instance(spell);
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(spellDealDamage(amount, target, "Rage Extractor deals damage equal to that spell's converted mana cost to target creature or player."));
		}
	}

	public RageExtractor(GameState state)
	{
		super(state);

		// Whenever you cast a spell with p in its mana cost, Rage Extractor
		// deals damage equal to that spell's converted mana cost to target
		// creature or player.
		this.addAbility(new RageExtractorAbility0(state));
	}
}
